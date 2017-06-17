package cn.tim.mail.entity;

/**
 * Created by LuoLiBing on 16/3/4.
 */
public class ServiceSearchCriteria {

    private String productSkuName;
    private Long id;

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceSearchCriteria(String productSkuName, Long id) {
        this.productSkuName = productSkuName;
        this.id = id;
    }

    public ServiceSearchCriteria() {
    }
}
