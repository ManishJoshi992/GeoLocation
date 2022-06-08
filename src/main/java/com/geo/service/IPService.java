package com.geo.service;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.glassfish.jersey.internal.guava.CacheBuilder;
import org.glassfish.jersey.internal.guava.CacheLoader;
import org.glassfish.jersey.internal.guava.LoadingCache;
import org.hibernate.SessionFactory;

import com.geo.entity.LocationInfo;

import io.dropwizard.hibernate.AbstractDAO;

public class IPService extends AbstractDAO<LocationInfo> {

	public IPService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public LocationInfo searchIPDB(String ip) {
		return (LocationInfo) namedQuery("findByIp").setParameter("ip", ip);
	}

	public Optional<LocationInfo> searchIP(String ip) {
		LoadingCache<String, Optional<LocationInfo>> cache = CacheBuilder.newBuilder().maximumSize(1000)
				.expireAfterAccess(1, TimeUnit.MINUTES).build(new CacheLoader<String, Optional<LocationInfo>>() {
					@Override
					public Optional<LocationInfo> load(String ip) throws IOException {
						return Optional.ofNullable(searchIPDB(ip));
					}
				});

		Optional<LocationInfo> ipDetails = Optional.ofNullable(searchIPDB(ip));
		if (cache != null) {
			return ipDetails;
		} else {
			LocationInfo ipdet = get(ip);
			save(ipdet);
			return Optional.ofNullable(ipdet);
		}
	}

	public LocationInfo save(@Valid LocationInfo ipDetails) {
		return persist(ipDetails);
	}
}
