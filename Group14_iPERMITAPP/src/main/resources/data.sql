INSERT INTO environmental_permit (permitid, permit_name, permit_fee, description)
VALUES ('AIR', 'Air Permit', 100.0, 'Air quality permit')
ON CONFLICT (permitid) DO NOTHING;

INSERT INTO environmental_permit (permitid, permit_name, permit_fee, description)
VALUES ('WASTE', 'Waste Permit', 200.0, 'Waste disposal permit')
ON CONFLICT (permitid) DO NOTHING;