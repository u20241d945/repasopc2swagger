package com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest;

import com.caterpillar.platform.pc2repasoswagger.mining.application.commandservices.MiningEquipmentUnitCommandService;
import com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest.resources.CreateMiningEquipmentUnitResource;
import com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest.resources.MiningEquipmentUnitResource;
import com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest.transform.CreateMiningEquipmentUnitCommandFromResourceAssembler;
import com.caterpillar.platform.pc2repasoswagger.mining.interfaces.rest.transform.MiningEquipmentUnitResourceFromEntityAssembler;
import com.caterpillar.platform.pc2repasoswagger.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST entry point for mining equipment registration.
 *
 * @author Gabriel Yanac Flores
 */
@RestController
@RequestMapping(
        value = "/api/v1/equipment-units",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Mining Equipment Units", description = "Caterpillar mining equipment registration")
public class MiningEquipmentUnitsController {

    private final MiningEquipmentUnitCommandService commandService;

    public MiningEquipmentUnitsController(MiningEquipmentUnitCommandService commandService) {
        this.commandService = commandService;
    }

    /** Registers one mining equipment unit. */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Register a mining equipment unit",
            description = "Creates a mining equipment unit with an encrypted serial number")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Equipment unit registered successfully",
                    content = @Content(schema = @Schema(implementation = MiningEquipmentUnitResource.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Serial number already exists at the mine site",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<?> createEquipmentUnit(
            @Valid @RequestBody CreateMiningEquipmentUnitResource resource) {
        var command = CreateMiningEquipmentUnitCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MiningEquipmentUnitResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }
}
