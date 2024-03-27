package hello.Spring.discount;

import hello.Spring.member.Grade;
import hello.Spring.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    public static final int DISCOUNT_FIX_AMOUNT = 1_000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return DISCOUNT_FIX_AMOUNT;
        }

        return 0;
    }

}
