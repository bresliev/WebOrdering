insert into wo_sort_per_class
select rownum+1, 22, klasifikacija#, 22, null  from ocp_klasifikacija
where vrsta_klasifikacije# = 22
and klasifikacija# not in (select nvl(klasifikacija#_nad, '-1') from ocp_klasifikacija where vrsta_klasifikacije# = 22)
and klasifikacija#!= '000101';



insert into wo_classs_order
select 9+rownum, id, 22, wo_klasifikacija#, 1, null
from wo_sort_per_class
