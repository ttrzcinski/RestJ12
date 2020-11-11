#!/bin/bash
echo "Will try to reinstall docker now - need to run with sudo."
# Remove standard docker
sudo snap remove docker
# Docker removed -> install docker
sudo snap install docker
# Should be docker 19.03.11 from Canonical✓ installed
echo "DONE"
