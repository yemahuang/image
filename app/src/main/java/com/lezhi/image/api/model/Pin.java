package com.lezhi.image.api.model;

import com.lezhi.image.api.Api;

/**
 * Created by lezhi on 2017/2/26.
 */

public class Pin {

    String pin_id;
    ImageFile file;
    Board board;

    public static class ImageFile {
        public static final String FW320_SF = "_fw320sf";
        public static final String FW658 = "_fw658";
        public static final String SQ75_SF = "_sq75sf";
        String key;
        String type;
        String width;
        String height;

        public String getImageUrl(String type) {
            return Api.IMAGE_BASE_URL + getKey() + type;
        }

        public String getImageUrl() {
            return Api.IMAGE_BASE_URL + getKey() + FW320_SF;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }
    }

    public static class Board {
        String title;
        String description;
        String category_id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }
    }

    public String getPin_id() {
        return pin_id;
    }

    public void setPin_id(String pin_id) {
        this.pin_id = pin_id;
    }

    public ImageFile getFile() {
        return file;
    }

    public void setFile(ImageFile file) {
        this.file = file;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
