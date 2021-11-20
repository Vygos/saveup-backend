INSERT INTO USUARIO (id_usuario, cpf, dt_cadastro, email, nome, secret, vl_renda)
VALUES (nextval('usuario_sequence'), '00000000000', '2000-01-01', 'sistema@saveup.com.br', 'SISTEMA',
        '$2a$10$ubl8D3d318udA1/HW8jVquq4ZMQ0DQOcYbwYJAJv4wVuNEP8.rA3e', 0.0);

INSERT INTO TIPO_GANHO(id_tipo_ganho, nome, email) VALUES (nextval('tipo_ganho_sequence'), 'SALÁRIO', null);
INSERT INTO TIPO_GANHO(id_tipo_ganho, nome, email) VALUES (nextval('tipo_ganho_sequence'), 'HORAS EXTRAS', null);
INSERT INTO TIPO_GANHO(id_tipo_ganho, nome, email) VALUES (nextval('tipo_ganho_sequence'), 'APOSTAS', null);
INSERT INTO TIPO_GANHO(id_tipo_ganho, nome, email) VALUES (nextval('tipo_ganho_sequence'), 'CRIPTOMOEDA', null);
INSERT INTO TIPO_GANHO(id_tipo_ganho, nome, email) VALUES (nextval('tipo_ganho_sequence'), 'OUTROS', null);

INSERT INTO TIPO_DESPESA(id_tipo_despesa, nome, email) VALUES (nextval('tipo_despesa_sequence'), 'LUZ', null);
INSERT INTO TIPO_DESPESA(id_tipo_despesa, nome, email) VALUES (nextval('tipo_despesa_sequence'), 'ÁGUA', null);
INSERT INTO TIPO_DESPESA(id_tipo_despesa, nome, email) VALUES (nextval('tipo_despesa_sequence'), 'EDUCAÇÃO', null);
INSERT INTO TIPO_DESPESA(id_tipo_despesa, nome, email) VALUES (nextval('tipo_despesa_sequence'), 'ALUGUEL', null);
INSERT INTO TIPO_DESPESA(id_tipo_despesa, nome, email) VALUES (nextval('tipo_despesa_sequence'), 'COMPRAS (MERCADO)', null);