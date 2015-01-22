# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table lift (
  id                        varchar(255) not null,
  name                      varchar(255),
  seats                     integer,
  constraint pk_lift primary key (id))
;

create table meeting (
  id                        varchar(255) not null,
  date                      timestamp,
  constraint pk_meeting primary key (id))
;

create table skiarena (
  id                        varchar(255) not null,
  name                      varchar(255),
  constraint pk_skiarena primary key (id))
;

create table skier (
  id                        integer not null,
  username                  varchar(255),
  password                  varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  birthdate                 timestamp,
  gender                    integer,
  is_online                 boolean,
  constraint pk_skier primary key (id))
;

create sequence lift_seq;

create sequence meeting_seq;

create sequence skiarena_seq;

create sequence skier_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists lift;

drop table if exists meeting;

drop table if exists skiarena;

drop table if exists skier;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists lift_seq;

drop sequence if exists meeting_seq;

drop sequence if exists skiarena_seq;

drop sequence if exists skier_seq;

