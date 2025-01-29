package com.geolocation_service.controller;

import com.geolocation_service.model.BaseResponse;
import com.geolocation_service.model.Suggestion;
import com.geolocation_service.service.GeolocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GeolocationController {
    @Autowired
    private final GeolocationService geolocationService;

    @Operation(
            summary = "get suggestion location",
            description = "get suggestion location with auto-complete parameter")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            BaseResponse.class))
            ),
    }
    )
    @GetMapping(path = "/suggestion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> getSuggestion(@RequestParam(name = "q") String query,
                                                      @RequestParam(name = "latitude", required = false) Double latitude,
                                                      @RequestParam(name = "longitude", required = false) Double longitude) {

        List<Suggestion> responseList = geolocationService.getSuggestion(query, latitude, longitude);

        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.toString(), "Suggestion list", responseList));
    }
}
