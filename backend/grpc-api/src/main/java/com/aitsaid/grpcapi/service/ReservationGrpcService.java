package com.aitsaid.grpcapi.service;

import com.hotel.reservation.grpc.*;
import com.hotel.reservation.services.ReservationService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@Transactional
public class ReservationGrpcService extends ReservationServiceGrpc.ReservationServiceImplBase {

    private final ReservationService reservationService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

    public ReservationGrpcService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void getReservationById(GetReservationByIdRequest request, StreamObserver<GetReservationByIdResponse> responseObserver) {
        reservationService.getReservationById(request.getId()).ifPresentOrElse(
                reservation -> {
                    GetReservationByIdResponse response = GetReservationByIdResponse.newBuilder()
                            .setReservation(mapReservation(reservation))
                            .build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                },
                () -> responseObserver.onError(new RuntimeException("Reservation not found"))
        );
    }

    @Override
    public void getAllReservations(GetAllReservationsRequest request, StreamObserver<GetAllReservationsResponse> responseObserver) {
        List<Reservation> grpcReservations = reservationService.getAllReservations().stream()
                .map(this::mapReservation)
                .collect(Collectors.toList());

        GetAllReservationsResponse response = GetAllReservationsResponse.newBuilder()
                .addAllReservations(grpcReservations)
                .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createReservation(CreateReservationRequest request, StreamObserver<CreateReservationResponse> responseObserver) {
        com.hotel.reservation.entities.Reservation entity = new com.hotel.reservation.entities.Reservation();
        
        com.hotel.reservation.entities.Client client = new com.hotel.reservation.entities.Client();
        client.setId(request.getClientId());
        
        com.hotel.reservation.entities.Chambre chambre = new com.hotel.reservation.entities.Chambre();
        chambre.setId(request.getChambreId());
        
        entity.setClient(client);
        entity.setChambre(chambre);
        entity.setDateDebut(LocalDate.parse(request.getDateDebut(), formatter));
        entity.setDateFin(LocalDate.parse(request.getDateFin(), formatter));
        entity.setPreferences(request.getPreferences());
        
        com.hotel.reservation.entities.Reservation created = reservationService.createReservation(entity);
        
        CreateReservationResponse response = CreateReservationResponse.newBuilder()
                .setReservation(mapReservation(created))
                .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateReservation(UpdateReservationRequest request, StreamObserver<UpdateReservationResponse> responseObserver) {
        com.hotel.reservation.entities.Reservation updated = reservationService.updateReservation(
                request.getId(),
                request.getClientId(),
                request.getChambreId(),
                LocalDate.parse(request.getDateDebut(), formatter),
                LocalDate.parse(request.getDateFin(), formatter),
                request .getPreferences()
        );
        
        UpdateReservationResponse response = UpdateReservationResponse.newBuilder()
                .setReservation(mapReservation(updated))
                .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteReservation(DeleteReservationRequest request, StreamObserver<DeleteReservationResponse> responseObserver) {
        reservationService.deleteReservation(request.getId());
        
        DeleteReservationResponse response = DeleteReservationResponse.newBuilder()
                .setSuccess(true)
                .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private Reservation mapReservation(com.hotel.reservation.entities.Reservation entity) {
        Client client = Client.newBuilder()
                .setId(entity.getClient().getId())
                .setNom(entity.getClient().getNom())
                .setPrenom(entity.getClient().getPrenom())
                .setEmail(entity.getClient().getEmail())
                .setTelephone(entity.getClient().getTelephone())
                .build();

        Chambre chambre = Chambre.newBuilder()
                .setId(entity.getChambre().getId())
                .setType(entity.getChambre().getType().toString())
                .setPrix(entity.getChambre().getPrix().doubleValue())
                .build();

        return Reservation.newBuilder()
                .setId(entity.getId())
                .setClient(client)
                .setChambre(chambre)
                .setDateDebut(entity.getDateDebut().toString())
                .setDateFin(entity.getDateFin().toString())
                .setPreferences(entity.getPreferences() != null ? entity.getPreferences() : "")
                .build();
    }
}
