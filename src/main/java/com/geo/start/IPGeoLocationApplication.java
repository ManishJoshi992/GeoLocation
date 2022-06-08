package com.geo.start;

import javax.ws.rs.client.Client;

import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geo.controller.GeoLocation;
import com.geo.entity.LocationInfo;
import com.geo.service.IPService;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class IPGeoLocationApplication extends Application<IPGeoLocationConfiguration> {

	public static void main(String[] args) throws Exception {
		new IPGeoLocationApplication().run(args);
	}

	@Override
	public void run(IPGeoLocationConfiguration IPGeoLocationConfiguration, Environment environment) throws Exception {
		IPService IPDAO = new IPService(hibernateBundle.getSessionFactory());

		final Client client = new JerseyClientBuilder(environment).using(IPGeoLocationConfiguration.getJerseyClient())
				.build(getName());

		environment.jersey().register(new GeoLocation(IPDAO, client, IPGeoLocationConfiguration.getApiURL()));

		final JdbiFactory jdbifactory = new JdbiFactory();
		final Jdbi jdbi = jdbifactory.build(environment, IPGeoLocationConfiguration.getDatabase(), "mysql");
		final IPService dao = jdbi.onDemand(IPService.class);
		environment.jersey().register(new GeoLocation(dao, client, IPGeoLocationConfiguration.getApiURL()));
	}

	HibernateBundle<IPGeoLocationConfiguration> hibernateBundle = new HibernateBundle<IPGeoLocationConfiguration>(
			LocationInfo.class) {
		public PooledDataSourceFactory getDataSourceFactory(IPGeoLocationConfiguration IPGeoLocationConfiguration) {
			return IPGeoLocationConfiguration.getDatabase();
		}
	};

	@Override
	public void initialize(Bootstrap<IPGeoLocationConfiguration> bootstrap) {
		bootstrap.addBundle(hibernateBundle);
	}
}
