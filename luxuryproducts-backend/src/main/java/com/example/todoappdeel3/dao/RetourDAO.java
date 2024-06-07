package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.RetourRequestDTO;
import com.example.todoappdeel3.models.*;
import com.example.todoappdeel3.repositories.*;
import com.example.todoappdeel3.utils.StaticDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class RetourDAO {

    private final RetourReasonRepository retourReasonRepository;
    private final RetourRequestRepository retourRequestRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public RetourDAO(RetourReasonRepository retourReasonRepository, RetourRequestRepository retourRequestRepository, UserRepository userRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.retourReasonRepository = retourReasonRepository;
        this.retourRequestRepository = retourRequestRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<RetourReason> getAllReasons() {
        return this.retourReasonRepository.findAll();
    }

    public RetourRequest createRetourRequest(RetourRequestDTO retourRequestDTO) {
        CustomUser user = getCurrentUser();

        PlacedOrder order = findOrderById(retourRequestDTO.orderId);
        validateRetourPeriod(order.getOrderDate());

        RetourReason retourReason = findRetourReasonById(retourRequestDTO.reasonId);

        Set<OrderItem> retouredProducts = findOrderItemsByIds(retourRequestDTO.orderItemIds);

        RetourRequest retourRequest = buildRetourRequest(user, retourRequestDTO, order, retourReason, retouredProducts);

        retourRequestRepository.save(retourRequest);

        return retourRequest;
    }

    private CustomUser getCurrentUser() {
        return userRepository.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    private PlacedOrder findOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    private void validateRetourPeriod(LocalDateTime orderDate) {
        long daysBetween = ChronoUnit.DAYS.between(orderDate, LocalDateTime.now());
        if (daysBetween > StaticDetails.RETOUR_PERIOD_DAYS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create return request for orders placed more than " + StaticDetails.RETOUR_PERIOD_DAYS + " days ago");
        }
    }

    private RetourReason findRetourReasonById(UUID reasonId) {
        return retourReasonRepository.findById(reasonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reason not found"));
    }

    private Set<OrderItem> findOrderItemsByIds(List<Long> orderProductIds) {
        Set<OrderItem> retouredProducts = new HashSet<>();
        for (Long orderProductId : orderProductIds) {
            OrderItem orderProduct = orderItemRepository.findById(orderProductId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OrderProduct not found"));
            retouredProducts.add(orderProduct);
        }
        return retouredProducts;
    }

    private RetourRequest buildRetourRequest(CustomUser user, RetourRequestDTO retourRequestDTO, PlacedOrder order, RetourReason retourReason, Set<OrderItem> retouredProducts) {
        RetourRequest retourRequest = new RetourRequest();
        retourRequest.setUser(user);
        retourRequest.setDateTime(LocalDateTime.now());
        retourRequest.setNote(retourRequestDTO.comment);
        retourRequest.setState(StaticDetails.RETOUR_PENDING);
        retourRequest.setOrder(order);
        retourRequest.setReason(retourReason.getReason());
        retourRequest.setRetouredProducts(retouredProducts);
        return retourRequest;
    }
}