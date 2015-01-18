# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table interests (
  m_interest                varchar(255) not null,
  constraint pk_interests primary key (m_interest))
;

create table location (
  m_name                    varchar(255) not null,
  m_latitude                double,
  m_longitude               double,
  constraint pk_location primary key (m_name))
;

create table meeting (
  m_datetime                timestamp,
  constraint pk_meeting))
;

create table skier (
  m_credit                  double not null,
  constraint pk_skier primary key (m_credit))
;

create table user (
  m_username                varchar(255) not null,
  m_password                varchar(255),
  m_firstname               varchar(255),
  m_lastname                varchar(255),
  m_birthday                timestamp,
  constraint pk_user primary key (m_username))
;

create sequence interests_seq;

create sequence location_seq;

create sequence meeting_seq;

create sequence skier_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists interests;

drop table if exists location;

drop table if exists meeting;

drop table if exists skier;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists interests_seq;

drop sequence if exists location_seq;

drop sequence if exists meeting_seq;

drop sequence if exists skier_seq;

drop sequence if exists user_seq;

