SELECT setval('training_id_seq', (SELECT MAX(id) FROM training));
