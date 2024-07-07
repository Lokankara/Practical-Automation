import csv
import random
import string


def create_char_pool(include_uppercase, include_lowercase, include_numbers, include_symbols):
    char_pool = ''
    if include_uppercase:
        char_pool += string.ascii_uppercase
    if include_lowercase:
        char_pool += string.ascii_lowercase
    if include_numbers:
        char_pool += string.digits
    if include_symbols:
        char_pool += '._'
    return char_pool


def generate_domain(length, domain=".com", include_uppercase=False, include_lowercase=True, include_numbers=False, include_symbols=False):
    char_pool = create_char_pool(include_uppercase, include_lowercase, include_numbers, include_symbols)
    return ''.join(random.choices(char_pool, k=length)) + domain


def generate_local(length, include_uppercase=True, include_lowercase=True, include_numbers=True, include_symbols=False):
    char_pool = create_char_pool(include_uppercase, include_lowercase, include_numbers, include_symbols)
    return ''.join(random.choices(char_pool, k=length))


def generate_email(len1, len2):
    local_part = generate_local(len1, True, True, True, True)
    domain_part = generate_domain(len2, ".com", False, True, False, False)
    return f"{local_part}@{domain_part}"


def generate_incorrect_email():
    incorrect_emails = []
    incorrect_emails.append(random.choice(string.digits + '._') + ''.join(random.choices(string.ascii_letters + string.digits, k=random.randint(1, 19))) + '@' + generate_domain(5))
    incorrect_emails.append('.' + ''.join(random.choices(string.ascii_letters + string.digits, k=random.randint(1, 19))) + '@' + generate_domain(5))
    incorrect_emails.append(''.join(random.choices(string.ascii_letters, k=random.randint(1, 19))) + '@' + ''.join(random.choices(string.ascii_letters + string.digits + '._', k=random.randint(1, 10))) + '.' + ''.join(random.choices(string.ascii_letters, k=random.randint(1, 10))))
    incorrect_emails.append(''.join(random.choices(string.ascii_letters, k=random.randint(1, 19))) + '@' + ''.join(random.choices(string.ascii_letters, k=random.randint(1, 10))) + '.' + random.choice(string.ascii_letters))
    incorrect_emails.append(''.join(random.choices(string.ascii_letters, k=random.randint(1, 19))) + '@' + ''.join(random.choices(string.ascii_letters, k=random.randint(1, 10))))
    return random.choice(incorrect_emails)


def generate_emails(local_lengths, domain_lengths, filename):
    with open(filename, 'w', newline='') as csvfile:
        email_writer = csv.writer(csvfile)
        for local_length in local_lengths:
            for domain_length in domain_lengths:
                email = generate_email(local_length, domain_length)
                email_writer.writerow([email])
                incorrect_email = generate_incorrect_email()
                email_writer.writerow([incorrect_email])


local_lengths = range(1, 3)
domain_lengths = range(1, 3)
filename = 'invalid_email.csv'
generate_emails(local_lengths, domain_lengths, filename)
