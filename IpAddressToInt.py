def IP2Int(ip):
    o = list(map(int, ip.split('.')))
    res = (o[0] * pow(256, 3)) + (o[1] * pow(256, 2)) + (o[2] * 256) + o[3]
    return res


def Int2IP(ipnum):
    o1 = int(ipnum / pow(256, 3)) % 256
    o2 = int(ipnum / pow(256, 2)) % 256
    o3 = int(ipnum / 256) % 256
    o4 = int(ipnum) % 256
    return '%(o1)s.%(o2)s.%(o3)s.%(o4)s' % locals()

# Example
print('192.168.0.1 -> %s' % IP2Int('192.168.0.1'))
print('3232235521 -> %s' % Int2IP(3232235521))
