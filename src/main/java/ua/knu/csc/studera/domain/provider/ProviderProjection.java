package ua.knu.csc.studera.domain.provider;

public class ProviderProjection implements SimpleProvider {
    private final Integer id;
    private final String name;
    private final String description;

    public ProviderProjection(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
