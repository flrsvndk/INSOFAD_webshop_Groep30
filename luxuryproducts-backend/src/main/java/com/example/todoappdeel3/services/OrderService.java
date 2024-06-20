package com.example.todoappdeel3.services;

import com.example.todoappdeel3.dao.*;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.dto.OrderItemDTO;
import com.example.todoappdeel3.models.*;
import com.example.todoappdeel3.repositories.OrderItemRepository;
import com.example.todoappdeel3.repositories.ProductSpecificationTypesRepository;
import com.example.todoappdeel3.repositories.PromocodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    private final OrderItemDAO orderItemDAO;
    private final AdressDAO adressDAO;
    private final ProductSpecificationTypesRepository productSpecificationTypesRepository;
    private final OrderItemRepository orderItemRepository;
    private final PromocodeRepository promocodeRepository;
    private final PromocodeDAO promocodeDAO;

    public OrderService(OrderItemDAO orderItemDAO,
                        AdressDAO adressDAO,
                        ProductSpecificationTypesRepository productSpecificationTypesRepository,
                        OrderItemRepository orderItemRepository,
                        PromocodeRepository promocodeRepository,
                        PromocodeDAO promocodeDAO) {
        this.orderItemDAO = orderItemDAO;
        this.adressDAO = adressDAO;
        this.productSpecificationTypesRepository = productSpecificationTypesRepository;
        this.orderItemRepository = orderItemRepository;
        this.promocodeRepository = promocodeRepository;
        this.promocodeDAO = promocodeDAO;
    }

    public PlacedOrder createOrder(OrderDTO orderDTO, CustomUser user){

        List<OrderItem> orderItems = new ArrayList<>();

        LocalDateTime orderDate = LocalDateTime.now();

        Adress adress = adressDAO.createAdress(orderDTO.adressDTO);

        Promocode promocode = getPromocode(orderDTO);

        double totalPricebeforePromocode = 0;
        double totalPriceAfterPromocode = 0;

        PlacedOrder customerOrder = new PlacedOrder(
                orderDate, orderDTO.notes, user, adress, promocode, totalPricebeforePromocode, totalPriceAfterPromocode
        );

        for (OrderItemDTO orderItemDTO : orderDTO.orderItems) {
            OrderItem orderItem = orderItemDAO.createOrderItem(orderItemDTO, customerOrder);
            orderItem.setQuantity(this.checkProductQuanity(orderItem.getProductType().getId(), orderItem.getQuantity()));

            totalPricebeforePromocode +=  orderItem.getProductType().getPrice() * orderItem.getQuantity();

            orderItems.add(orderItem);
            this.orderItemRepository.save(orderItem);
        }
        customerOrder.setOrderItems(orderItems);

        if (orderDTO.promocode != null) {
            totalPriceAfterPromocode = (totalPricebeforePromocode * (1 - ((double) orderDTO.promocode.percentageOff / 100)));
            this.promocodeDAO.updateAnalyticsPromocode(customerOrder);
        } else {
            totalPriceAfterPromocode = totalPricebeforePromocode;
        }

        customerOrder.setTotalPriceBeforePromocode(totalPricebeforePromocode);
        customerOrder.setTotalPriceAfterPromocode(totalPriceAfterPromocode);

        return customerOrder;
    }

    public int checkProductQuanity(UUID productId, int quanity) {
        Optional<ProductSpecificationType> orderedProduct = this.productSpecificationTypesRepository.findById(productId);
        if (orderedProduct.isPresent()){
            ProductSpecificationType product = orderedProduct.get();
            if (quanity < product.getStock()) {
                product.setStock(product.getStock() - quanity);
            } else {
                quanity = product.getStock();
                product.setStock(0);
            }
            return quanity;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No Product found for that Id"
        );
    }

    private Promocode getPromocode(OrderDTO orderDTO) {
        Promocode promocode = null;

        if (orderDTO.promocode != null) {
            Optional<Promocode> optionalPromocode = this.promocodeRepository.findById(orderDTO.promocode.id);
            if (optionalPromocode.isPresent()) {
                promocode = optionalPromocode.get();
            }
        }
        return promocode;
    }
}
