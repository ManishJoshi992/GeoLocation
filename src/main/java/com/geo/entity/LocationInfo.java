package com.geo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "LOCATION")

@NamedQueries({ @NamedQuery(name = "findByIpInDB", query = "select * from LOCATION loc where lov.IPADDR = :ip") })

public class LocationInfo {

	@Id
	@Column(name = "ID")
	@NotEmpty
	@NotNull
	private int ID;

	@Column(name = "IPADDR")
	private String ipaddr;

	@Column(name = "CONTINENT")
	private String continent;

	@Column(name = "CONTINENT_CODE")
	private String continent_code;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "COUNTRY_CODE")
	private String country_code;

	@Column(name = "REGION")
	private String region;

	@Column(name = "REGION_NAME")
	private String region_name;

	@Column(name = "CITY")
	private String city;

	@Column(name = "DISTRICT")
	private String district;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "TIME_ZONE")
	private String time_zone;
}
