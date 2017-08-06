package com.lezhi.image.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.lezhi.image.api.Api;

/**
 * Created by lezhi on 2017/2/26.
 */

public class Pin implements Parcelable {

    String pin_id;
    ImageFile file;
    Board board;

    protected Pin(Parcel in) {
        pin_id = in.readString();
        file = in.readParcelable( ImageFile.class.getClassLoader() );
        board = in.readParcelable( Board.class.getClassLoader() );
    }

    public static final Creator<Pin> CREATOR = new Creator<Pin>() {
        @Override
        public Pin createFromParcel(Parcel in) {
            return new Pin( in );
        }

        @Override
        public Pin[] newArray(int size) {
            return new Pin[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( pin_id );
        dest.writeParcelable( file, flags );
        dest.writeParcelable( board, flags );
    }


    public static class ImageFile implements Parcelable {
        public static final String FW320_SF = "_fw320sf";
        public static final String FW658 = "_fw658";
        public static final String SQ75_SF = "_sq75sf";
        String key;
        String type;
        String width;
        String height;

        protected ImageFile(Parcel in) {
            key = in.readString();
            type = in.readString();
            width = in.readString();
            height = in.readString();
        }

        public static final Creator<ImageFile> CREATOR = new Creator<ImageFile>() {
            @Override
            public ImageFile createFromParcel(Parcel in) {
                return new ImageFile( in );
            }

            @Override
            public ImageFile[] newArray(int size) {
                return new ImageFile[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString( key );
            dest.writeString( type );
            dest.writeString( width );
            dest.writeString( height );
        }
    }

    public static class Board implements Parcelable {
        String title;
        String description;
        String category_id;

        protected Board(Parcel in) {
            title = in.readString();
            description = in.readString();
            category_id = in.readString();
        }

        public static final Creator<Board> CREATOR = new Creator<Board>() {
            @Override
            public Board createFromParcel(Parcel in) {
                return new Board( in );
            }

            @Override
            public Board[] newArray(int size) {
                return new Board[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString( title );
            dest.writeString( description );
            dest.writeString( category_id );
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
