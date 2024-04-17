create table conversion
(
    id             int auto_increment,
    amount         numeric(30, 15) NOT NULL,
    time_stamp      timestamp NOT NULL,
    source_currency varchar(4) NOT NULL,
    target_currency varchar(4) NOT NULL,
    uuid           varchar(100) NOT NULL
);

insert into conversion (amount, time_stamp, source_currency, target_currency, uuid)
VALUES (30.46, '2024-04-15T14:44:55', 'BGN', 'BGN', '38988fd7-1a24-428e-b560-ed9b63bd3220'),
       (188.46, '2024-04-10T14:44:55', 'BGN', 'DJF', '2e5c0519-0eb5-4a8e-acfe-974b8528693d'),
       (200.46, '2024-04-04T14:44:55', 'BGN', 'DOP', '9a7134b2-d2c0-4f1b-b08e-8e8770ecec2f'),
       (10.46, '2024-04-01T14:44:55', 'BGN', 'JMD', '0136bd53-aa88-42e9-b786-ab7634a3abda'),
       (110.46, '2024-04-08T14:44:55', 'BGN', 'KHR', 'a94efeab-ac2b-4448-a4ab-a75330b4de82'),
       (20.46, '2024-03-08T14:44:55', 'BGN', 'CVE', '19d83a48-0bf4-4e4f-8dde-a53085d70f10'),
       (1.46, '2024-02-08T14:44:55', 'BGN', 'LSL', '673ae447-f932-4abd-b4ad-c91e8902fb49'),
       (5.46, '2024-01-08T14:44:55', 'BGN', 'ATS', '8d6550f1-c471-43a9-a318-74b36a3d7ab3'),
       (1000.46, '2024-04-02T14:44:55', 'BGN', 'MDL', '28fb4eca-8970-437a-9ca1-4bd63428235e'),
       (34.46, '2024-04-03T14:44:55', 'BGN', 'MVR', '1f1bb79c-1253-4973-903c-255235136066'),
       (56.46, '2024-04-08T13:44:55', 'BGN', 'NIO', 'd1b3f3b5-59cf-47e5-bc5c-d631a1b3464e'),
       (98.46, '2024-04-08T18:44:55', 'BGN', 'BGN', '64c75586-b0d1-4165-a0b4-d6f8ddf791c3'),
       (200.46, '2024-04-08T12:44:55', 'BGN', 'BGN', '96a60659-4199-4a5f-a305-9b357dab6eb4')
;