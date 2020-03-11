package ro.fortech.internship.vinylshop.item.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.fortech.internship.vinylshop.item.model.ItemStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class ItemDisplayDto {

    private UUID id;

    private String name;

    private Double price;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    private Integer quantity;
}
