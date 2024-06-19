package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.GiftcardDAO;
import com.example.todoappdeel3.dto.GiftcardBuyerDTO;
import com.example.todoappdeel3.dto.GiftcardOwnerDTO;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.Giftcard;
import com.example.todoappdeel3.repositories.GiftcardRepository;
import com.example.todoappdeel3.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestGiftcardGet {
    private CustomUser user;
    GiftcardDAO giftcardDAO;
    Giftcard giftcard;

    @Mock
    private GiftcardRepository giftcardRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void initTestGiftcard() {
        giftcardDAO = new GiftcardDAO(this.giftcardRepositoryMock, this.userRepositoryMock);
        this.user = new CustomUser(
                "name", "infix", "lastName", "admin@mail.com",
                "Hallo123!", "imgUrl"
        );
        this.user.setRole("");
        this.giftcard = new Giftcard("message", 2, 2, this.user, this.user);
    }


    //getGiftcardByOwner
    @Test
    public void should_returnGiftcards_when_getGiftcardByOwner() {
        when(this.userRepositoryMock.findByEmail(any())).thenReturn(new CustomUser(
                "name", "infix", "lastName", "admin@mail.com",
                "Hallo123!", "imgUrl"
        ));
        List<Giftcard> giftcards = new ArrayList<>();
        giftcards.add(giftcard);
        when(this.giftcardRepositoryMock.findByOwner(any())).thenReturn(giftcards);
        List<GiftcardOwnerDTO> result = this.giftcardDAO.getGiftcardsByOwner(user.getEmail());
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void should_returnGiftcards_when_getGiftcardByOwnerAdmin() {
        when(this.userRepositoryMock.findByEmail(any())).thenReturn(user);
        List<Giftcard> giftcards = new ArrayList<>();
        giftcards.add(giftcard); giftcards.add(giftcard);
        when(this.giftcardRepositoryMock.findAll()).thenReturn(giftcards);
        List<GiftcardOwnerDTO> result = this.giftcardDAO.getAllGiftcards(user.getEmail());
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    //getGiftcardByBuyer
    @Test
    public void should_returnGiftcards_when_getGiftcardByBuyer() {
        when(this.userRepositoryMock.findByEmail(any())).thenReturn(new CustomUser(
                "name", "infix", "lastName", "admin@mail.com",
                "Hallo123!", "imgUrl"
        ));
        List<Giftcard> giftcards = new ArrayList<>();
        giftcards.add(giftcard);
        when(this.giftcardRepositoryMock.findByBuyer(any())).thenReturn(giftcards);
        List<GiftcardBuyerDTO> result = this.giftcardDAO.getGiftcardsByBuyer(user.getEmail());
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }
}
