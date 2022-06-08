package com.geo.controller;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

import com.geo.entity.LocationInfo;
import com.geo.service.IPService;

import io.dropwizard.hibernate.UnitOfWork;

@Produces(MediaType.APPLICATION_JSON)
public class GeoLocation implements IGeoLocation {

	private final IPService ipService;
	private final Client client;
	private final String apiURL;

	public GeoLocation(IPService ipDAO, Client client, String apiURL) {
		this.ipService = ipDAO;
		this.client = client;
		this.apiURL = apiURL;
	}

	@GET
	@Path("/{ip}")
	@UnitOfWork
	public Optional<LocationInfo> getIpDetails(@PathParam("ip") String ip) {
		return ipService.searchIP(ip);
	}
}
