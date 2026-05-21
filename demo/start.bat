@echo off
echo Running application infrastructure stack...
docker compose down
docker compose up --build
pause