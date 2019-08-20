
set nocount on
Select 'Duplicate Columns'
SELECT 
Case When Len(page_table_name) <= 30 
THEN  page_table_name+Stuff(SPACE(30 -Len(page_table_name)),1, 30 -Len(page_table_name),Replicate(' ',30 -Len(page_table_name))) 
ELSE page_table_name END + ' - '+  attrib_ID  FROM sy_obj_att INNER JOIN SY_PG ON SY_PG.object_id = sy_obj_att.object_id 
where page_table_name is not null 
GROUP BY page_table_name, attrib_ID HAVING COUNT(*)>1 ORDER BY page_table_name, attrib_ID 
Select 'Missing Dictionary Columns'
set nocount off
select 
Case When Len(table_name) <= 30 
THEN  table_name+Stuff(SPACE(30 -Len(table_name)),1, 30 -Len(table_name),Replicate(' ',30 -Len(table_name))) 
ELSE table_name END + ' - '+  column_name from information_schema.columns isc
where not exists
(select * from sy_obj obj 
INNER JOIN sy_obj_att att ON obj.af_row_id = att.object_id 
where object_table=isc.TABLE_NAME and attrib_id=isc.COLUMN_NAME ) 
and  table_name in  (select Table_name from INFORMATION_SCHEMA.tables ist where  exists (select * from sy_obj where object_table=ist.TABLE_NAME))
order by table_name,column_name
