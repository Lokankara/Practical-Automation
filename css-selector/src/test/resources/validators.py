import re


def validate_password(password):
    pattern = (
        r'^(?=.*[a-z])'
        r'(?=.*[A-Z])'
        r'(?=.*[!@#$%^&*()\-_=+{};:,<.>])'
        r'.{8,20}$'
    )
    if re.match(pattern, password):
        return True
    else:
        return False


def validate_email(email):
    lowercase_start_pattern = r'^[a-z]'
    valid_characters_pattern = r'^[a-zA-Z0-9.@_]+$'
    dot_before_at_pattern = r'(?<!\.)@'
    domain_letters_pattern = r'@[a-zA-Z]+$'
    host_letters_pattern = r'@[a-zA-Z]+\.'
    last_domain_length_pattern = r'\.[a-zA-Z]{2,}$'
    last_hostname_length_pattern = r'\.[a-zA-Z]+\.$'
    domain_sections_pattern = r'\..+\.'
    total_length_pattern = r'^.{1,20}$'
    combined_pattern = r'{}{}{}{}{}{}{}{}{}'.format(lowercase_start_pattern, valid_characters_pattern,
                                                    dot_before_at_pattern, domain_letters_pattern,
                                                    host_letters_pattern, last_domain_length_pattern,
                                                    last_hostname_length_pattern, domain_sections_pattern,
                                                    total_length_pattern)

    if re.match(combined_pattern, email):
        return True
    else:
        return False
