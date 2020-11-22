ALTER TABLE ocp_vrsta_klasifikacije
ADD CONSTRAINT vrkl_pk PRIMARY KEY (vrsta_klasifikacije#);

ALTER TABLE ocp_klasifikacija
ADD CONSTRAINT kl_pk PRIMARY KEY (vrsta_klasifikacije#, klasifikacija#);

ALTER TABLE ocp_atribut
ADD CONSTRAINT atr_pk PRIMARY KEY (atribut#);

create table wo_sort_per_class(
id number primary key, 
wo_vrsta_klasifikacije# number, 
wo_klasifikacija# varchar2(50), 
ocp_vrsta_klasifikacije# number,
constraint wosortperclass_woclass_fk foreign key (wo_vrsta_klasifikacije#,wo_klasifikacija#) 
                                        references ocp_klasifikacija(vrsta_klasifikacije#, klasifikacija#),
constraint wosortperclass_vrklas_fk foreign key (ocp_vrsta_klasifikacije#) references ocp_vrsta_klasifikacije(vrsta_klasifikacije#));


create table wo_classs_order(
id number,
woSortPerClassId number,
ocp_vrsta_klasifikacije# number,
ocp_klasifikacija# varchar2(50),
sort number,
ocp_klasifikaijca_superior varchar2(50),
constraint woclassorder_wosort_fk foreign key (woSortPerClassId) references wo_sort_per_class(id),
constraint woclassorder_woclass_fk foreign key (ocp_vrsta_klasifikacije#,ocp_klasifikacija#) 
                                        references ocp_klasifikacija(vrsta_klasifikacije#, klasifikacija#));


create table wo_sort_per_object_attribute(
id number,
woSortPerClassId number,
attribute_owner varchar2(50),
attribute_name varchar2(200),
attribute_id number,
sort number,
order_type varchar2(250),
constraint woclassorder_woattr_fk foreign key (woSortPerClassId) references wo_sort_per_class(id),
constraint wosortperattr_attribute_fk foreign key (attribute_id) references ocp_atribut(atribut#));
