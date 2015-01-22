# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table interests (
  id                        integer not null,
  constraint pk_interests primary key (id))
;

create table lift (
  id                        integer not null,
  name                      varchar(255),
  seats                     integer,
  constraint pk_lift primary key (id))
;

create table meeting (
  id                        varchar(255) not null,
  date                      timestamp,
  constraint pk_meeting primary key (id))
;

create table session (
  id                        varchar(255) not null,
  skier_id                  integer,
  constraint pk_session primary key (id))
;

create table skiarena (
  id                        integer not null,
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
  is_online                 boolean,
  current_location_id       integer,
  constraint pk_skier primary key (id))
;


create table interests_skier (
  interests_id                   integer not null,
  skier_id                       integer not null,
  constraint pk_interests_skier primary key (interests_id, skier_id))
;

create table meeting_skier (
  meeting_id                     varchar(255) not null,
  skier_id                       integer not null,
  constraint pk_meeting_skier primary key (meeting_id, skier_id))
;

create table skier_interests (
  skier_id                       integer not null,
  interests_id                   integer not null,
  constraint pk_skier_interests primary key (skier_id, interests_id))
;

create table skier_meeting (
  skier_id                       integer not null,
  meeting_id                     varchar(255) not null,
  constraint pk_skier_meeting primary key (skier_id, meeting_id))
;
create sequence interests_seq;

create sequence lift_seq;

create sequence meeting_seq;

create sequence session_seq;

create sequence skiarena_seq;

create sequence skier_seq;

alter table skier add constraint fk_skier_current_location_1 foreign key (current_location_id) references skiarena (id) on delete restrict on update restrict;
create index ix_skier_current_location_1 on skier (current_location_id);



alter table interests_skier add constraint fk_interests_skier_interests_01 foreign key (interests_id) references interests (id) on delete restrict on update restrict;

alter table interests_skier add constraint fk_interests_skier_skier_02 foreign key (skier_id) references skier (id) on delete restrict on update restrict;

alter table meeting_skier add constraint fk_meeting_skier_meeting_01 foreign key (meeting_id) references meeting (id) on delete restrict on update restrict;

alter table meeting_skier add constraint fk_meeting_skier_skier_02 foreign key (skier_id) references skier (id) on delete restrict on update restrict;

alter table skier_interests add constraint fk_skier_interests_skier_01 foreign key (skier_id) references skier (id) on delete restrict on update restrict;

alter table skier_interests add constraint fk_skier_interests_interests_02 foreign key (interests_id) references interests (id) on delete restrict on update restrict;

alter table skier_meeting add constraint fk_skier_meeting_skier_01 foreign key (skier_id) references skier (id) on delete restrict on update restrict;

alter table skier_meeting add constraint fk_skier_meeting_meeting_02 foreign key (meeting_id) references meeting (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists interests;

drop table if exists interests_skier;

drop table if exists lift;

drop table if exists meeting;

drop table if exists meeting_skier;

drop table if exists session;

drop table if exists skiarena;

drop table if exists skier;

drop table if exists skier_interests;

drop table if exists skier_meeting;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists interests_seq;

drop sequence if exists lift_seq;

drop sequence if exists meeting_seq;

drop sequence if exists session_seq;

drop sequence if exists skiarena_seq;

drop sequence if exists skier_seq;

