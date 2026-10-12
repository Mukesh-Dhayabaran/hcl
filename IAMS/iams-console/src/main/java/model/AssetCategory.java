
package model;

public class AssetCategory extends BaseEntity {

    private String categoryName;
    private String description;

    public AssetCategory(int categoryId, String categoryName,
                         String description) {
        super(categoryId);
        this.categoryName = categoryName;
        this.description = description;
    }

    public int getCategoryId() {
        return getId();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
