package com.example.domain.common;

public class EnumType {
    public static class Gender {
        public static final String FEMALE = "0";
        public static final String MALE = "1";
    }

    public static class MediaType {
        public static final String VIDEO = "0";
        public static final String ARTICLE = "1";
    }

    public static class CommentStatus {
        public static final String PENDING_REVIEW = "0";
        public static final String NORMAL = "1";
        public static final String HIDE = "2";
    }
}
