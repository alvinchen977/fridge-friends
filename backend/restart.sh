#!/bin/bash
sudo systemctl restart nginx
sudo systemctl restart gunicorn
echo "System successfully restarted!"
