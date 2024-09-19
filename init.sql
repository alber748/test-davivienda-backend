-- ELIMINAR TABLAS EXISTENTES
DROP TABLE IF EXISTS solicitudes CASCADE;
DROP TABLE IF EXISTS persona CASCADE;
DROP TABLE IF EXISTS actividades_economicas CASCADE;
DROP TABLE IF EXISTS estado_civil CASCADE;
DROP TABLE IF EXISTS forma_pago CASCADE;

-- CREAR TABLA ACTIVIDADES_ECONOMICAS
CREATE TABLE actividades_economicas (
      id_actividad_economica BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
      descripcion VARCHAR(255) NOT NULL
);

-- CREAR TABLA ESTADO_CIVIL
CREATE TABLE estado_civil (
      id_estado_civil BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
      descripcion VARCHAR(100) NOT NULL
);

-- CREAR TABLA PERSONA (Reemplaza a clientes)
CREATE TABLE persona (
      id_persona BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
      dui VARCHAR(20) UNIQUE,
      nit VARCHAR(20) UNIQUE,
      nombres VARCHAR(100) NOT NULL,
      apellidos VARCHAR(100) NOT NULL,
      sexo VARCHAR(1),
      id_actividad_economica BIGINT,
      id_estado_civil BIGINT,
      CONSTRAINT fk_actividad_economica FOREIGN KEY (id_actividad_economica) REFERENCES actividades_economicas(id_actividad_economica),
      CONSTRAINT fk_estado_civil FOREIGN KEY (id_estado_civil) REFERENCES estado_civil(id_estado_civil)
);

-- CREAR TABLA FORMA_PAGO
CREATE TABLE forma_pago (
      id_forma_pago BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
      descripcion VARCHAR(255) NOT NULL
);

-- CREAR TABLA SOLICITUDES (Con relaciones a persona y forma_pago)
CREATE TABLE solicitudes (
      id_solicitud BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
      id_persona BIGINT,
      fecha_creacion DATE NOT NULL,
      monto DECIMAL(10, 2) NOT NULL,
      plazo INT NOT NULL,
      id_forma_pago BIGINT,
      CONSTRAINT fk_persona_solicitud FOREIGN KEY (id_persona) REFERENCES persona(id_persona),
      CONSTRAINT fk_forma_pago FOREIGN KEY (id_forma_pago) REFERENCES forma_pago(id_forma_pago)
);

-- INSERTAR DATOS EN ACTIVIDADES_ECONOMICAS
INSERT INTO actividades_economicas (descripcion) VALUES
      ('Agricultura'),
      ('Comercio'),
      ('Tecnología'),
      ('Construcción');

-- INSERTAR DATOS EN ESTADO_CIVIL
INSERT INTO estado_civil (descripcion) VALUES
      ('Soltero'),
      ('Casado'),
      ('Divorciado'),
      ('Viudo');

-- INSERTAR DATOS EN FORMA_PAGO
INSERT INTO forma_pago (descripcion) VALUES
      ('Efectivo'),
      ('Tarjeta de crédito'),
      ('Transferencia bancaria'),
      ('Cheque');