all_tags = open('result.txt', 'r', encoding='utf-8')

data = all_tags.read().strip().split('\n')

insta_map = dict()

for line in data:
	if line == '':
		continue

	tag, index = line.split(',')
	insta_map[tag] = index

tiktok_map = dict()

tiktok_file = open("tags_from_tiktok.txt", 'r', encoding='utf-8')

tiktok_tags = tiktok_file.read().strip().split('\n')

cnt = 1
for tag in tiktok_tags:
	try:
		ind = insta_map[tag]
	except Exception:
		data.append(tag + ',100000000')
		pass
	tiktok_map[tag] = cnt
	cnt += 1

cnt2 = 1

iter_number = 1

output_file = open("xml.txt", 'w', encoding='utf-8')
output_file.write("<tags>\n")

for tag in data:
	if iter_number % 10000 == 0:
		print(iter_number)
	iter_number += 1

	tag, _ = tag.split(',')
	x = ''

	try:
		x = tiktok_map[tag]
	except Exception:
		x = cnt
		cnt += 1

	try:
		output_file.write("\t<record name=\"" + tag + "\" instagram_id=\"" + str(insta_map[tag]) + "\" tiktok_id=\"" + str(x) + "\" />\n")
	except Exception:
		output_file.write("\t<record name=\"" + tag + "\" instagram_id=\"" + str(1000000 + cnt2) + "\" tiktok_id=\"" + str(x) + "\" />\n")
		cnt2 += 1

output_file.write("</tags>")
output_file.close()
