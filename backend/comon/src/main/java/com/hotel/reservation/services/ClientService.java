package com.hotel.reservation.services;

import com.hotel.reservation.entities.Client;

import java.util.List;

/**
 * @author radouane
 **/
public interface ClientService {

    Client createClient(Client client);
    Client getClientById(Long id);
    Client getClientByEmail(String email);
    List<Client> getAllClients();
    Client updateClient(Long id, Client request);
    void deleteClient(Long id);
    long count();

}
