package hello.Spring.discount;

import hello.Spring.member.Grade;
import hello.Spring.member.Member;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy {

    public static final int DISCOUNT_PERCENT = 10;
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * DISCOUNT_PERCENT / 100;
        }

         return 0;
    }
}
