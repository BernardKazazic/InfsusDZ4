package org.example.domain.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.domain.model.Review;
import org.jooq.DSLContext;

import static org.example.Tables.REVIEW;

@RequiredArgsConstructor
public class ReviewRepository {
    @NonNull
    private DSLContext dslContext;

    public Review createReview(String userId, String reviewText, Boolean valid) {
        try {
            return dslContext.insertInto(REVIEW)
                    .columns(REVIEW.USER_ID, REVIEW.REVIEW_, REVIEW.VALID)
                    .values(userId, reviewText, valid)
                    .returning()
                    .fetchSingleInto(Review.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Review publishReview(Integer reviewId) {
        try {
            return dslContext.update(REVIEW)
                    .set(REVIEW.VALID, true)
                    .where(REVIEW.ID.eq(reviewId))
                    .returning()
                    .fetchSingleInto(Review.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteReview(Integer reviewId) {
        return dslContext.deleteFrom(REVIEW).where(REVIEW.ID.eq(reviewId)).execute() > 0;
    }
}
