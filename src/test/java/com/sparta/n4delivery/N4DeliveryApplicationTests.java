package com.sparta.n4delivery;

import com.sparta.n4delivery.enums.UserType;
import com.sparta.n4delivery.menu.entity.Menu;
import com.sparta.n4delivery.menu.repository.MenuRepository;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.order.entity.OrderDetails;
import com.sparta.n4delivery.order.repository.OrderDetailsRepository;
import com.sparta.n4delivery.order.repository.OrderRepository;
import com.sparta.n4delivery.reviwe.entity.Review;
import com.sparta.n4delivery.reviwe.repository.ReviewRepository;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.store.repository.StoreRepository;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class N4DeliveryApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Rollback(false)
    void sampleData() {

        List<User> users = new ArrayList<>();
        for (int idx = 0; idx < 10; idx++) {
            String randomName = RandomStringUtils.randomAlphanumeric(10);
            users.add(User.builder()
                    .email(randomName + "@gmail.com")
                    .password("1234")
                    .nickname(randomName)
                    .build());
        }

        List<User> owners = new ArrayList<>();
        for (int idx = 0; idx < 10; idx++) {
            String randomName = RandomStringUtils.randomAlphanumeric(10);
            owners.add(User.builder()
                    .email(randomName + "@gmail.com")
                    .password("1234")
                    .nickname(randomName)
                    .type(UserType.OWNER)
                    .build());
        }
        userRepository.saveAll(users);
        userRepository.saveAll(owners);

        List<Store> stores = new ArrayList<>();
        for (User owner : owners) {
            for (int idx = 0; idx < Math.random() * 3; idx++) {
                String randomName = RandomStringUtils.randomAlphanumeric(10);
                stores.add(Store.builder()
                        .user(owner)
                        .name(randomName)
                        .openedAt(LocalTime.of(9, 0))
                        .closedAt(LocalTime.of(22, 0))
                        .minimumAmount(5000)
                        .build());
            }
        }
        storeRepository.saveAll(stores);

        List<Menu> menus = new ArrayList<>();
        for (Store store : stores) {
            for (int idx = 0; idx < 10; idx++) {
                String randomName = RandomStringUtils.randomAlphanumeric(10);
                Random random = new Random();
                int randomInt = random.nextInt(490000) + 1000; // 1000 ~ 500000
                menus.add(Menu.builder()
                        .store(store)
                        .name(randomName)
                        .price(randomInt)
                        .build());
            }
        }
        menuRepository.saveAll(menus);


        List<Order> orders = new ArrayList<>();
        for (User user : users) {
            for (int idx = 0; idx < 10; idx++) {
                Store Store = stores.get((int) (Math.random() * stores.size()));
                orders.add(Order.builder()
                        .user(user)
                        .store(Store)
                        .build());
            }
        }

        List<OrderDetails> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            Store store = order.getStore();
            int totalPrice = 0;
            for (int idx = 0; idx < Math.random() * 10; idx++) {
                List<Menu> storeMenus = store.getMenus();
                Menu menu = storeMenus.get((int) (Math.random() * storeMenus.size()));
                int menuCnt = (int) (Math.random() * 10 + 1);
                orderDetails.add(OrderDetails.builder()
                        .order(order)
                        .menu(menu)
                        .count(menuCnt)
                        .price(menu.getPrice())
                        .build());

                totalPrice += menu.getPrice() * menuCnt;
            }
            order.setTotalPrice(totalPrice);
        }
        orderRepository.saveAll(orders);
        orderDetailsRepository.saveAll(orderDetails);

        List<Review> reviews = new ArrayList<>();
        for (Order order : orders) {
            String randomComment = RandomStringUtils.randomAlphanumeric(50);
            reviews.add(Review.builder()
                    .order(order)
                    .user(order.getUser())
                    .store(order.getStore())
                    .score(Math.random() * 5)
                    .comment(randomComment)
                    .build());
        }
        reviewRepository.saveAll(reviews);
    }
}
