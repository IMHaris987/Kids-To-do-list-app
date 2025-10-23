import os
import re
from xml.etree import ElementTree as ET

# Folder containing your project
PROJECT_PATH = r"D:\Kids To Do LIst\app\src\main\res\layout"
OUTPUT_FILE = "app/src/main/res/values/generated_strings.xml"

# Regex patterns for XML and Kotlin
XML_TEXT_PATTERN = re.compile(r'android:(text|hint|label)\s*=\s*"([^"]+)"')
KOTLIN_TEXT_PATTERN = re.compile(r'text\s*=\s*"([^"]+)"|Toast\.makeText\(.*?,\s*"([^"]+)"')

def extract_strings_from_file(file_path):
    with open(file_path, "r", encoding="utf-8") as f:
        content = f.read()

    results = []
    for match in XML_TEXT_PATTERN.findall(content):
        results.append(match[1])
    for match in KOTLIN_TEXT_PATTERN.findall(content):
        text = match[0] or match[1]
        if text:
            results.append(text)
    return results

def generate_string_name(text):
    name = re.sub(r'[^a-zA-Z0-9_]', '_', text.strip().lower())
    return f"auto_{name[:40]}"

def main():
    strings_found = {}
    for root, _, files in os.walk("."):
        for file in files:
            if file.endswith(".xml") or file.endswith(".kt"):
                file_path = os.path.join(root, file)
                for text in extract_strings_from_file(file_path):
                    if len(text.strip()) > 1 and not text.startswith("@"):
                        key = generate_string_name(text)
                        strings_found[key] = text

    if not strings_found:
        print("ℹ️ No new hardcoded strings found.")
        return

    os.makedirs(os.path.dirname(OUTPUT_FILE), exist_ok=True)

    root = ET.Element("resources")
    for key, value in strings_found.items():
        string_elem = ET.SubElement(root, "string", name=key)
        string_elem.text = value

    tree = ET.ElementTree(root)
    tree.write(OUTPUT_FILE, encoding="utf-8", xml_declaration=True)

    print(f"✅ Extracted {len(strings_found)} strings → {OUTPUT_FILE}")

if __name__ == "__main__":
    main()
