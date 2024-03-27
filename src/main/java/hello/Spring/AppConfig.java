package hello.Spring;

import hello.Spring.discount.DiscountPolicy;
import hello.Spring.discount.RateDiscountPolicy;
import hello.Spring.member.MemberService;
import hello.Spring.member.MemberServiceImpl;
import hello.Spring.member.MemoryMemberRepository;
import hello.Spring.order.OrderService;
import hello.Spring.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 클래스자체에는 @Configuration
@Configuration
public class AppConfig {

    // 메소드에는 @Bean
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }


}
