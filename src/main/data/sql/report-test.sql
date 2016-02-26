SELECT
ss.day as day,
SUM(s.service_hours + s.opening_hours + s.closing_hours) as schedule
FROM tbl_store_schedules as ss, tbl_employee_schedules as es, tbl_shifts as s
WHERE ss.store_schedule_id = es.store_schedule_id
AND es.employee_schedule_id = s.employee_schedule_id
AND s.position_id IS NOT NULL
AND ss.day BETWEEN STR_TO_DATE('2010-11-28','%Y-%m-%d') AND STR_TO_DATE('2010-11-28','%Y-%m-%d')
AND ss.store_id = 1043
GROUP BY ss.day
ORDER BY ss.day

SELECT
s.staffing_date as day,
SUM(total_daily_target) as target
FROM tbl_staffing as s, tbl_positions as p
WHERE s.position_id = p.position_id
AND s.staffing_date BETWEEN STR_TO_DATE('2010-11-21','%Y-%m-%d') AND STR_TO_DATE('2010-11-21','%Y-%m-%d')
AND p.store_id = #store_id#
GROUP BY s.staffing_date
ORDER BY s.staffing_date