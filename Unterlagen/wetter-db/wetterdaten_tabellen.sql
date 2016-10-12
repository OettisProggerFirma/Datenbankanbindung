DROP TABLE IF EXISTS wettermessung;
DROP TABLE IF EXISTS wetterstation;
CREATE TABLE wetterstation (
  s_id       INTEGER PRIMARY KEY NOT NULL,
  standort   TEXT                NOT NULL,
  geo_breite NUMERIC(6, 3),
  geo_laenge NUMERIC(6, 3),
  hoehe      INTEGER,
  betreiber  TEXT
);
CREATE UNIQUE INDEX wetterstation_s_id_uindex ON wetterstation (s_id);

CREATE TABLE wettermessung
(
  fk_s_id               INTEGER NOT NULL,
  datum                 DATE    NOT NULL,
  qualitaet             INTEGER,
  min_5cm               DOUBLE PRECISION,
  min_2m                DOUBLE PRECISION,
  mittel_2m             DOUBLE PRECISION,
  max_2m                DOUBLE PRECISION,
  relative_feuchte      DOUBLE PRECISION,
  mittel_windstaerke    DOUBLE PRECISION,
  max_windstaerke       DOUBLE PRECISION,
  sonnenscheindauer     DOUBLE PRECISION,
  mittel_bedeckungsgrad DOUBLE PRECISION,
  niederschlagshoehe    DOUBLE PRECISION,
  mittel_luftdruck      DOUBLE PRECISION,
  CONSTRAINT wettermessung_pkey PRIMARY KEY (fk_s_id, datum),
  CONSTRAINT fk_station_messung FOREIGN KEY (fk_s_id) REFERENCES wetterstation (s_id)
);