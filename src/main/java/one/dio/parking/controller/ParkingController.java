package one.dio.parking.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.dio.parking.controller.dto.ParkingCreateDTO;
import one.dio.parking.controller.dto.ParkingDTO;
import one.dio.parking.controller.mapper.ParkingMapper;
import one.dio.parking.model.Parking;
import one.dio.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static one.dio.parking.service.ParkingService.getUUID;

@RestController
@RequestMapping("/parking")
@Api(tags = "Parking Controller")
public class ParkingController {


    private final ParkingService parkingService;

    private final ParkingMapper parkingMapper;

    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper){
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    @ApiOperation("Find all parkings")
    public ResponseEntity<List<ParkingDTO>> findAll(){

        List<Parking>parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);

        }
    @GetMapping("/{id}")
    @ApiOperation("Find all by id")
    public ResponseEntity<ParkingDTO>findById(@PathVariable String id){
        Parking parking = parkingService.findById(id);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{id}")
    @ApiOperation("Delete by id")
    public ResponseEntity delete(@PathVariable String id) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @ApiOperation("Create entry parking")
    public ResponseEntity<ParkingDTO>create(@RequestBody ParkingCreateDTO dto){
        var parkingCreate= parkingMapper.toParkingCreate(dto);
        Parking parking = parkingService.create(parkingCreate);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PutMapping("/{id}")
    @ApiOperation("Update entry parking")
    public ResponseEntity<ParkingDTO>update(@PathVariable String id ,@RequestBody ParkingCreateDTO dto){
        var parkingCreate= parkingMapper.toParkingCreate(dto);
        Parking parking = parkingService.update(id, parkingCreate);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @PostMapping("/{id}")
    @ApiOperation("Exit car from parking")
    public ResponseEntity<ParkingDTO>checkOut(@PathVariable String id){
        Parking parking = parkingService.checkOut(id);
        return ResponseEntity.ok(parkingMapper.toParkingDTO(parking));
    }

}


