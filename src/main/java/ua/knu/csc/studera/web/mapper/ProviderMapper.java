package ua.knu.csc.studera.web.mapper;

import org.springframework.stereotype.Component;
import ua.knu.csc.studera.domain.provider.Provider;
import ua.knu.csc.studera.web.dto.CreateProviderDTO;

@Component
public class ProviderMapper {

    public Provider toEntity(CreateProviderDTO providerDTO) {
        return new Provider(null, providerDTO.getName(), providerDTO.getDescription(), null);
    }
}
