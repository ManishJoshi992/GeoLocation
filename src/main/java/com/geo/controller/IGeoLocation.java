package com.geo.controller;

import java.util.Optional;

import com.geo.entity.LocationInfo;

public interface IGeoLocation {

	public Optional<LocationInfo> getIpDetails(String ipAddress);

} 