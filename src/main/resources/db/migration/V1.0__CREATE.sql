create sequence despesa_sequence start 1 increment 1;
create sequence financa_sequence start 1 increment 1;
create sequence ganhos_sequence start 1 increment 1;
create sequence tipo_despesa_sequence start 1 increment 1;
create sequence tipo_ganho_sequence start 1 increment 1;
create sequence usuario_sequence start 1 increment 1;

create table despesa
(
    id_despesa      int4 not null,
    valor           numeric(19, 2),
    nome            varchar(255),
    id_financa      int4,
    id_tipo_despesa int4,
    primary key (id_despesa)
);

create table financa
(
    id_financa int4 not null,
    periodo    varchar(255),
    vl_base    numeric(19, 2),
    vl_margem  numeric(19, 2),
    id_usuario int4,
    primary key (id_financa)
);

create table ganhos
(
    id_ganho      int4 not null,
    nome          varchar(255),
    valor         numeric(19, 2),
    id_financa    int4,
    id_tipo_ganho int4,
    primary key (id_ganho)
);

create table tipo_despesa
(
    id_tipo_despesa int4 not null,
    email           varchar(255),
    nome            varchar(255),
    primary key (id_tipo_despesa)
);

create table tipo_ganho
(
    id_tipo_ganho int4 not null,
    email         varchar(255),
    nome          varchar(255),
    primary key (id_tipo_ganho)
);

create table usuario
(
    id_usuario    int4         not null,
    cpf           varchar(255),
    dt_cadastro   timestamp    not null,
    dt_nascimento date,
    email         varchar(255) not null,
    foto          varchar(255),
    nome          varchar(255) not null,
    secret        varchar(255) not null,
    vl_renda      numeric(19, 2),
    primary key (id_usuario)
);

alter table despesa
    add constraint FK_DESPESA_ID_FINANCA foreign key (id_financa) references financa;
alter table despesa
    add constraint FK_DESPESA_ID_TIPO_DESPESA foreign key (id_tipo_despesa) references tipo_despesa;
alter table financa
    add constraint FK_FINANCA_ID_USUARIO foreign key (id_usuario) references usuario;
alter table ganhos
    add constraint FK_GANHOS_ID_FINANCA foreign key (id_financa) references financa;
alter table ganhos
    add constraint FK_GANHOS_ID_TIPO_GANHO foreign key (id_tipo_ganho) references tipo_ganho;
