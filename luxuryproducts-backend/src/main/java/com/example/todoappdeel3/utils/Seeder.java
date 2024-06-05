package com.example.todoappdeel3.utils;

import com.example.todoappdeel3.dao.CategoryDAO;
import com.example.todoappdeel3.dao.ProductDAO;
import com.example.todoappdeel3.dao.UserRepository;
import com.example.todoappdeel3.dto.CategoryDTO;
import com.example.todoappdeel3.dto.ProductDTO;
import com.example.todoappdeel3.dto.ProductSpecificationsDTO;
import com.example.todoappdeel3.dto.TypeDTO;
import com.example.todoappdeel3.models.Category;
import com.example.todoappdeel3.models.CustomUser;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Seeder {
    private final CategoryDAO categoryDAO;
    private ProductDAO productDAO;
    private UserRepository userRepository;


    public Seeder(ProductDAO productDAO, UserRepository userRepository, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.userRepository = userRepository;
        this.categoryDAO = categoryDAO;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event){
        this.seedProducts();
        this.seedUser();
    }

    private void seedProducts(){
        long category = categoryDAO.createCategory("Horloges").getId();
        long category2 = categoryDAO.createCategory("Kleding").getId();
        long category3 = categoryDAO.createCategory("Tassen").getId();
        long category4 = categoryDAO.createCategory("Sieraden").getId();
        long category5 = categoryDAO.createCategory("Auto's").getId();

        List<TypeDTO> typesDTO2b = new ArrayList<>();
        List<TypeDTO> typesDTO2c = new ArrayList<>();
        List<TypeDTO> typesDTO3a = new ArrayList<>();

        TypeDTO typeb1a = new TypeDTO("S", 10005.00, 3, "https://assets.vogue.com/photos/64e3d0a6903d9fdc472cae41/3:4/w_748%2Cc_limit/slide_3.jpg", null);
        TypeDTO typeb1b = new TypeDTO("M", 10004.00, 3, "https://assets.vogue.com/photos/64e3d0a6903d9fdc472cae41/3:4/w_748%2Cc_limit/slide_3.jpg", null);
        TypeDTO typeb1c = new TypeDTO("L", 10000.00, 3, "https://assets.vogue.com/photos/64e3d0a6903d9fdc472cae41/3:4/w_748%2Cc_limit/slide_3.jpg", null);
        TypeDTO typeb2a = new TypeDTO("S", 10001.00, 3, "https://assets.vogue.com/photos/64e3d0a6903d9fdc472cae41/3:4/w_748%2Cc_limit/slide_3.jpg", null);
        TypeDTO typeb2b = new TypeDTO("M", 10003.00, 3, "https://assets.vogue.com/photos/64e3d0a6903d9fdc472cae41/3:4/w_748%2Cc_limit/slide_3.jpg", null);
        TypeDTO typeb2c = new TypeDTO("L", 10004.00, 3, "https://assets.vogue.com/photos/64e3d0a6903d9fdc472cae41/3:4/w_748%2Cc_limit/slide_3.jpg", null);
        TypeDTO typec1a = new TypeDTO("4", 130000.00, 8, "https://www.tesla.com/ownersmanual/images/GUID-5543BA62-932F-46C5-B1EF-44707D4726B2-online-en-US.png", null);

        typesDTO2b.add(typeb1a);
        typesDTO2b.add(typeb1b);
        typesDTO2b.add(typeb1c);
        typesDTO2c.add(typeb2a);
        typesDTO2c.add(typeb2b);
        typesDTO2c.add(typeb2c);
        typesDTO3a.add(typec1a);

        ProductSpecificationsDTO trenchSizea = new ProductSpecificationsDTO("size", typesDTO2b);
        ProductSpecificationsDTO trenchSizeb = new ProductSpecificationsDTO("size", typesDTO2c);
        ProductSpecificationsDTO teslaWielen = new ProductSpecificationsDTO("wielen", typesDTO3a);


        List<TypeDTO> typesDTO = new ArrayList<>();
        List<TypeDTO> typesDTO2 = new ArrayList<>();
        List<TypeDTO> typesDTO3 = new ArrayList<>();
        List<TypeDTO> typesDTO4 = new ArrayList<>();
        List<TypeDTO> typesDTO5 = new ArrayList<>();
        List<TypeDTO> typesDTO6 = new ArrayList<>();
        List<TypeDTO> typesDTO7 = new ArrayList<>();
        List<TypeDTO> typesDTO8 = new ArrayList<>();
        List<TypeDTO> typesDTO9 = new ArrayList<>();


        TypeDTO typea1 = new TypeDTO("18kt gold",55000.00 , 8, "https://i.ibb.co/Z170ptk/image-2024-05-21-081438902.png", null);
        TypeDTO typea2 = new TypeDTO("silver", 55003.00, 8, "https://schapi-products.s3.eu-central-1.amazonaws.com/3523776/m126500ln-0001_modelpage_front_facing_landscape.png", null);
        typesDTO.add(typea1);
        typesDTO.add(typea2);
        ProductSpecificationsDTO specificationsDTO = new ProductSpecificationsDTO("materiaal", typesDTO);
        ProductDTO productDTO1 = new ProductDTO(
                "Rolex Cosmograph Daytona", "Deze Rolex Daytona, bekend om zijn ongeëvenaarde precisie en iconische status, is het ultieme statussymbool voor de elite.",
                null, category, specificationsDTO);



        TypeDTO typeb1 = new TypeDTO("Waterbestendig Katoen", null, 0, "https://assets.vogue.com/photos/64e3d0a6903d9fdc472cae41/3:4/w_748%2Cc_limit/slide_3.jpg", trenchSizea);
        TypeDTO typeb2 = new TypeDTO("Polyester", null, 0, "https://assets.vogue.com/photos/64e3d0a6903d9fdc472cae41/3:4/w_748%2Cc_limit/slide_3.jpg", trenchSizeb);
        typesDTO2.add(typeb1);
        typesDTO2.add(typeb2);
        ProductSpecificationsDTO specificationsDTO2 = new ProductSpecificationsDTO("materiaal", typesDTO2);
        ProductDTO productDTO2 = new ProductDTO(
                "Burberry Bespoke Trench Coat", "Handgemaakt in Engeland met exclusieve materialen, deze Burberry trenchcoat biedt tijdloze elegantie en luxe.",
                null, category2, specificationsDTO2);


        TypeDTO typec1 = new TypeDTO("Lamshuid leder", 8000.00,8, "https://images.vestiairecollective.com/images/resized/w=1024,q=75,f=auto,/produit/black-leather-timeless-classique-chanel-handbag-41859734-1_2.jpg", null);
        typesDTO3.add(typec1);
        ProductSpecificationsDTO specificationsDTO3 = new ProductSpecificationsDTO("materiaal", typesDTO3);
        ProductDTO productDTO3 = new ProductDTO("Chanel Classic Flap Bag",
                "Deze Chanel tas, met zijn iconische quiltdesign en kettingschouderband, is een symbool van luxueuze mode.",
                null, category3,specificationsDTO3  );


        TypeDTO typed1 = new TypeDTO("Roestvrijstaal", 40000.00, 8, "https://theswisscollector.com/22433/audemars-piguet-royal-oak-offshore.jpg" , null);
        typesDTO4.add(typed1);
        ProductSpecificationsDTO specificationsDTO4 = new ProductSpecificationsDTO("materiaal", typesDTO4);
        ProductDTO productDTO4 = new ProductDTO("Audemars Piguet Royal Oak Offshore",
                "De Royal Oak Offshore, met zijn gedurfde, innovatieve design, vertegenwoordigt de hoogste standaard van luxueuze horlogerie.",
                null, category, specificationsDTO4  );


        TypeDTO typee1 = new TypeDTO("Monogram canvas", 4500.00, 8, "https://www.winkelstraat.nl/cdn-cgi/image/w=1280,h=1280,format=auto/img/2700/2700/trim/catalog/product/4/3/4356647_wsnl736-5i3pol5qt306t4jt-image_default.jpg", null);
        typesDTO5.add(typee1);
        ProductSpecificationsDTO specificationsDTO5 = new ProductSpecificationsDTO("materiaal", typesDTO5);
        ProductDTO productDTO8 = new ProductDTO("Louis Vuitton Keepall Bandoulière 55",
                "De Keepall Bandoulière 55 van Louis Vuitton, perfect voor de wereldreiziger, combineert luxe met functionaliteit.",
                null, category3, specificationsDTO5 );


        TypeDTO typef1 = new TypeDTO("100% zijde", 1200.00, 8, "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSb58_lh7Su_uS6a0Hjs_4OK3eXA5PcS6kPMprUBGwke_pRxePxNuzMQlo45iVpIbJaKYf8F4wWdQQL9Gec5v3MZO2-pM8upDslLXHPf1EC_qa5a7eGjnBUkQ&usqp=CAE", null );
        typesDTO6.add(typef1);
        ProductSpecificationsDTO specificationsDTO6 = new ProductSpecificationsDTO("materiaal", typesDTO6);
        ProductDTO productDTO5 = new ProductDTO("Hermès Silk Scarf",
                "Deze Hermès sjaal, vervaardigd uit de fijnste zijde, toont een kunstzinnig ontwerp dat elegantie en cultuur uitstraalt.",
                null, category2, specificationsDTO6  );


        TypeDTO typeg1 = new TypeDTO("Elektrisch", null, 0, null, teslaWielen);
        typesDTO7.add(typeg1);
        ProductSpecificationsDTO specificationsDTO7 = new ProductSpecificationsDTO("materiaal", typesDTO7);
        ProductDTO productDTO6 = new ProductDTO("Tesla Model S Plaid",
                "De Tesla Model S Plaid, een topmodel in elektrische luxeauto's, biedt ongeëvenaarde prestaties en toonaangevende technologie.",
                null, category5, specificationsDTO7  );


        TypeDTO typeh1 = new TypeDTO("18kt goud", 6500.00, 8, "https://www.cartier.com/variants/images/1647597277058388/img1/w960.jpg", null);
        typesDTO8.add(typeh1);
        ProductSpecificationsDTO specificationsDTO8 = new ProductSpecificationsDTO("materiaal", typesDTO8);
        ProductDTO productDTO7 = new ProductDTO("Cartier Love Bracelet",
                "De Cartier Love Bracelet, versierd met diamanten, is een teken van tijdloze toewijding en luxe.",
                null, category4, specificationsDTO8);


        TypeDTO typej1 = new TypeDTO("Wol",3500.00, 8, "https://www.armani.com/variants/images/17266703523712898/F/w480.jpg", null);
        typesDTO9.add(typej1);
        ProductSpecificationsDTO specificationsDTO9 = new ProductSpecificationsDTO("materiaal", typesDTO9);
        ProductDTO productDTO9 = new ProductDTO("Giorgio Armani Black Label Suit",
                "Deze Giorgio Armani pak, uit de Black Label collectie, biedt ongeëvenaarde Italiaanse vakmanschap en stijl.",
                null, category2  ,specificationsDTO9);


        this.productDAO.createProduct(productDTO1);
        this.productDAO.createProduct(productDTO2);
        this.productDAO.createProduct(productDTO3);
        this.productDAO.createProduct(productDTO4);
        this.productDAO.createProduct(productDTO5);
        this.productDAO.createProduct(productDTO6);
        this.productDAO.createProduct(productDTO7);
        this.productDAO.createProduct(productDTO8);
        this.productDAO.createProduct(productDTO9);
    }


    private void seedUser(){
        CustomUser customUser = new CustomUser();
        customUser.setName("Bob");
        customUser.setInfix("");
        customUser.setLastName("Webshop");
        customUser.setEmail("bob@bobsluxuryenterprise.com");
        customUser.setPassword(new BCryptPasswordEncoder().encode("MegaGeheim6789!!"));
        customUser.setImgUrl("https://www.motortrend.com/uploads/2023/12/000-2024-mercedes-maybach-gls-600-front-three-quarter-view.jpg");
        customUser.setRole("ADMIN");
        userRepository.save(customUser);
    }
}
