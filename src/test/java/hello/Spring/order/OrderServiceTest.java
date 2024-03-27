package hello.Spring.order;

import hello.Spring.member.Grade;
import hello.Spring.member.Member;
import hello.Spring.member.MemberService;
import hello.Spring.member.MemberServiceImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10_000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
