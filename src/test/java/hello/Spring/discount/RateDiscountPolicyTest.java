package hello.Spring.discount;

import hello.Spring.member.Grade;
import hello.Spring.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    @Test
    void vip_o() {
        // Given
        Member member = new Member(1L, "memberVIP", Grade.VIP);

        // When
        int discount = discountPolicy.discount(member, 10_000);

        // Then
        Assertions.assertThat(discount).isEqualTo(1_000);
    }

    @DisplayName("VIP가 아닐 경우 할인이 적용되지 않아야 한다.")
    @Test
    void vip_x() {
        // Given
        Member member = new Member(1L, "memberVIP", Grade.BASIC);

        // When
        int discount = discountPolicy.discount(member, 10_000);

        // Then
        Assertions.assertThat(discount).isEqualTo(0);
    }

}