package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.GiftcardDAO;
import com.example.todoappdeel3.dto.GiftcardPurchaseDTO;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.Giftcard;
import com.example.todoappdeel3.repositories.CategoryRepository;
import com.example.todoappdeel3.repositories.GiftcardRepository;
import com.example.todoappdeel3.repositories.ProductRepository;
import com.example.todoappdeel3.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.http.HttpResponse;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestGiftcard {
    private CustomUser user;
    GiftcardDAO giftcardDAO;
    Giftcard giftcard;

    @Mock
    private ProductRepository productRepositoryMock;
    @Mock
    private CategoryRepository categoryRepositoryMock;
    @Mock
    private GiftcardRepository giftcardRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void initTestGiftcard() {
        giftcardDAO = new GiftcardDAO(this.giftcardRepositoryMock, this.userRepositoryMock);
        user = new CustomUser();
        user.setName("Bob");
        user.setInfix("");
        user.setLastName("Webshop");
        user.setEmail("bob@bobsluxuryenterprise.com");
        user.setPassword(new BCryptPasswordEncoder().encode("MegaGeheim6789!!"));
        user.setImgUrl("https://www.motortrend.com/uploads/2023/12/000-2024-mercedes-maybach-gls-600-front-three-quarter-view.jpg");
        user.setRole("ADMIN");
        this.giftcard = new Giftcard("message", 2, 2, this.user, this.user);
    }


    //createGiftcard
    @Test
    public void should_returnGiftcardCreated_when_createGiftcard() {
        when(this.userRepositoryMock.findByEmail(any())).thenReturn(user);
        GiftcardPurchaseDTO giftcardPurchaseDTO =
                new GiftcardPurchaseDTO("message", 1, user.getEmail());

        ResponseEntity<String> result = this.giftcardDAO.createGiftcard(giftcardPurchaseDTO, user.getEmail());
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("Giftcard created");
    }

    @Test
    public void should_returnReceiverOfThisGiftcardDoesntExist_when_createGiftcardWithWrongId() {
        when(this.userRepositoryMock.findByEmail(any())).thenReturn(null);
        GiftcardPurchaseDTO giftcardPurchaseDTO =
                new GiftcardPurchaseDTO("message", 1, user.getEmail());

        ResponseEntity<String> result = this.giftcardDAO.createGiftcard(giftcardPurchaseDTO, user.getEmail());
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("Receiver of this giftcard doesn't exist");
    }

    //updateGiftcard
    @Test
    public void should_returnUpdatedGiftcardValue_when_lowerGiftcardWithLowerValue() {
        when(this.giftcardRepositoryMock.findById(any())).thenReturn(Optional.of(giftcard));
        when(this.giftcardRepositoryMock.save(any())).thenReturn(giftcard);

        ResponseEntity<String> result = this.giftcardDAO.lowerGiftcardValue(1, 1L);
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("Updated giftcard value");
    }

    @Test
    public void should_returnNewGiftcardValueWasntBelowOldGiftcardValue_when_lowerGiftcardWithHigherValue() {
        when(this.giftcardRepositoryMock.findById(any())).thenReturn(Optional.of(giftcard));

        ResponseEntity<String> result = this.giftcardDAO.lowerGiftcardValue(3, 1L);
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("New giftcard value wasn't below old giftcard value");
    }

    @Test
    public void should_returnGiftcardWithThisIdDoesntExist_when_lowerGiftcardWithWrongId() {
        when(this.giftcardRepositoryMock.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<String> result = this.giftcardDAO.lowerGiftcardValue(3, 1L);
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("Giftcard with this id doesn't exist");
    }

    //chargeGiftcard
    @Test
    public void should_returnUpdatedGiftcardValue_when_chargeGiftcard() {
        when(this.giftcardRepositoryMock.findById(any())).thenReturn(Optional.of(giftcard));
        when(this.giftcardRepositoryMock.save(any())).thenReturn(giftcard);

        ResponseEntity<String> result = this.giftcardDAO.chargeGiftcard(100, 1L);
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("Updated giftcard value");
    }

    @Test
    public void should_returnGiftcardWithThisIdDoesntExist_when_chargeGiftcardWithWrongId() {
        when(this.giftcardRepositoryMock.findById(any())).thenReturn(Optional.empty());

        ResponseEntity<String> result = this.giftcardDAO.chargeGiftcard(100, 1L);
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("Giftcard with this id doesn't exist");
    }

    //deleteGiftcard
    @Test
    public void should_returnGiftcardCanceled_when_deleteGiftcard() {
        when(this.userRepositoryMock.findByEmail(any())).thenReturn(user);

        ResponseEntity<String> result = this.giftcardDAO.deleteGiftcard(1L, user.getEmail());
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("Giftcard canceled");
    }

    @Test
    public void should_returnYouDontHaveTheRightsToCancelAGiftcard_when_deleteGiftcardWithoutAdmin() {
        when(this.userRepositoryMock.findByEmail(any())).thenReturn(new CustomUser("name", "infix", "lastName", "email", "password"));

        ResponseEntity<String> result = this.giftcardDAO.deleteGiftcard(1L, user.getEmail());
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("You don't have the rights to cancel a giftcard");
    }

    @Test
    public void should_returnGiftcardWithThisIdDoesntExist_when_deleteGiftcardWithWrongEmail() {
        when(this.userRepositoryMock.findByEmail(any())).thenReturn(null);

        ResponseEntity<String> result = this.giftcardDAO.deleteGiftcard(1L, user.getEmail());
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("User with this email not found");
    }
}
