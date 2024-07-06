ALTER TABLE consultas ADD activo BOOLEAN;
UPDATE consultas SET activo = TRUE;
