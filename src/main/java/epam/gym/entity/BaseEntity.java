package epam.gym.entity;

public abstract class BaseEntity {

    private Long id;

    BaseEntity(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
