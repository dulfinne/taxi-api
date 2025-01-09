package com.dulfinne.taxi.driverservice.mapper;

import com.dulfinne.taxi.driverservice.dto.request.PointRequest;
import com.dulfinne.taxi.driverservice.dto.response.PointResponse;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Component;

@Component
public class PointMapper {
  private static final GeometryFactory geometryFactory =
      new GeometryFactory(new PrecisionModel(), 4326);

  public Point toPoint(PointRequest request) {
    Coordinate coordinate = new Coordinate(request.latitude(), request.longitude());
    return geometryFactory.createPoint(coordinate);
  }

  public PointResponse toResponse(Point point) {
    return new PointResponse(point.getX(), point.getY());
  }
}
