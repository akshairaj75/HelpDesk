import os
import re

service_dir = 'src/main/java/com/backend/helpdeskpro/service'
impl_dir = 'src/main/java/com/backend/helpdeskpro/serviceImpl'

# 1. Remove "extends CrudService<...>" from interfaces
for root, _, files in os.walk(service_dir):
    for f in files:
        if f.endswith('.java'):
            filepath = os.path.join(root, f)
            with open(filepath, 'r') as file:
                content = file.read()
            new_content = re.sub(r'\s*extends\s+CrudService<[^>]+>', '', content)
            if new_content != content:
                with open(filepath, 'w') as file:
                    file.write(new_content)

# 2. Remove "super(...);" from implementations
for root, _, files in os.walk(impl_dir):
    for f in files:
        if f.endswith('.java'):
            filepath = os.path.join(root, f)
            with open(filepath, 'r') as file:
                content = file.read()
            new_content = re.sub(r'\s*super\([^)]*\);', '', content)
            if new_content != content:
                with open(filepath, 'w') as file:
                    file.write(new_content)

print("Done")
