package entity;

/**
 * @author han56
 * @description 功能描述
 * @create 2023/3/23 下午3:12
 */
public class CommentEntity {

    private String comment;

    public CommentEntity(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
